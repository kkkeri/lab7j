package manager.DB;

import clientLog.ClientHandler;
import clientLog.PasswdHandler;
import collection.*;
import manager.CollectionManager;

import java.security.SecureRandom;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class PSQLmanager implements dbManager{
    @Override
    public Set<Flat> getCollectionFromDB() {
        LinkedHashSet<Flat> data = new LinkedHashSet<>();

        try (Connection connection = ConnectionF.getConnection();
             Statement statement = connection.createStatement()) {

            String query = "SELECT f.*, h.name, h.year, h.numberOfFlatsOnFloor, co.x, co.y " +
                    "FROM flat f " +
                    "JOIN coordinates co ON f.coordinates_id = co.id " +
                    "JOIN house h ON f.house_id = h.id";

            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                Coordinates coordinates = new Coordinates(resultSet.getLong("x"), resultSet.getDouble("y"));
                Date creationDate = resultSet.getDate("creation_date");
                Long area = resultSet.getLong("area");
                Integer numberOfRooms = resultSet.getInt("numberOfRooms");
                Furnish furnish = Furnish.valueOf(resultSet.getString("furnish"));
                View view = View.valueOf(resultSet.getString("view"));
                Transport transport = Transport.valueOf(resultSet.getString("transport"));
                House house = new House(resultSet.getString("name"), resultSet.getInt("year"), resultSet.getLong("numberOfFlatsOnFloor"));
                Flat flat = new Flat(id, name, coordinates, creationDate, area, numberOfRooms, furnish, view, transport, house);
                data.add(flat);
            }
            return data;
        }catch (SQLException e) {
            Logger.getLogger(PSQLmanager.class.getName()).warning("Something wrong" + e.getMessage());
            e.printStackTrace();
        }
        return data;
    }

    @Override
    public void writeCollectionToDB() {
        try (Connection connection = ConnectionF.getConnection()) {
            connection.setAutoCommit(false);

            Set<Long> existFlatId = new HashSet<>();
            String getFlatIdQuery = "SELECT id FROM flat";
            PreparedStatement getFlatIdStatement = connection.prepareStatement(getFlatIdQuery);
            ResultSet flatIdResultSet = getFlatIdStatement.executeQuery();
            while (flatIdResultSet.next()) {
                existFlatId.add(flatIdResultSet.getLong("id"));
            }

            for (Flat flat : CollectionManager.getInstance().getCollection()) {
                if(!existFlatId.contains(flat.getId())) {
                    flat.setId(addElementToDB(flat, connection));
                }
            }
            connection.commit();
        } catch (SQLException e) {
            Logger.getLogger(PSQLmanager.class.getName()).warning("Something wrong" + e.getMessage());
        }
    }

    public long writeObjToDB(Flat flat) {
        long generatedId = -1;
        try (Connection connection = ConnectionF.getConnection()) {
            connection.setAutoCommit(false);

            generatedId = addElementToDB(flat, connection);
            connection.commit();
        } catch (SQLException e) {
            Logger.getLogger(PSQLmanager.class.getName()).warning("Something wrong" + e.getMessage());
        }
        return generatedId;
    }

    public long addElementToDB(Flat flat, Connection connection) {
        long generatedId = -1;
        PreparedStatement inCoordStatement = null;
        PreparedStatement inHouseStatement = null;
        PreparedStatement inFlatStatement = null;
        PreparedStatement inCreatorStatement = null;

        try {
            connection.setAutoCommit(false);
            String inCoordQuery = "INSERT INTO coordinates (x, y) VALUES (?, ?) RETURNING id";
            inCoordStatement = connection.prepareStatement(inCoordQuery);
            inCoordStatement.setLong(1,flat.getCoordinates().getX());
            inCoordStatement.setDouble(2, flat.getCoordinates().getY());
            ResultSet coordResultSet = inCoordStatement.executeQuery();
            Logger.getLogger(PSQLmanager.class.getName()).info("Coord was added");

            int coordId = -1;
            if (coordResultSet.next()) {
                coordId = coordResultSet.getInt(1);
            }

            String inHouseQuery = "INSERT INTO house (name, year, numberOfFlatsOnFloor) VALUES (?, ?, ?) RETURNING id";
            inHouseStatement = connection.prepareStatement(inHouseQuery);
            inHouseStatement.setString(1, flat.getHouse().getName());
            inHouseStatement.setInt(2, flat.getHouse().getYear());
            inHouseStatement.setLong(3, flat.getHouse().getNumberOfFlatsOnFloor());
            ResultSet houseResultSet = inHouseStatement.executeQuery();
            Logger.getLogger(PSQLmanager.class.getName()).info("House was added");

            int houseId = -1;
            if (houseResultSet.next()) {
                houseId = houseResultSet.getInt(1);
            }

            String inFlatQuery = "INSERT INTO flat (name, coordinates_id, creation_date, area, numberOfRooms, furnish, view, transport, house_id)" +
                    "VALUES (?, ?, ?, ?, ?, CAST(? AS furnish_enum), CAST(? AS view_enum), CAST(? AS transport_enum), ?) RETURNING id";
            inFlatStatement = connection.prepareStatement(inFlatQuery);
            inFlatStatement.setString(1, flat.getName());
            inFlatStatement.setInt(2, coordId);
            inFlatStatement.setDate(3, new java.sql.Date(flat.getCreationDate().getTime()));
            inFlatStatement.setLong(4,flat.getArea());
            inFlatStatement.setInt(5, flat.getNumberOfRooms());
            inFlatStatement.setString(6, flat.getFurnish().toString());
            inFlatStatement.setString(7, flat.getView().toString());
            inFlatStatement.setString(8, flat.getTransport().toString());
            inFlatStatement.setInt(9, houseId);
            ResultSet flatResultSet = inFlatStatement.executeQuery();
            Logger.getLogger(PSQLmanager.class.getName()).info("Flat was added");
            if (flatResultSet.next()) {
                generatedId = flatResultSet.getLong(1);
            }

            String inCreatorQuery = "INSERT INTO creator (user_id, flat_id) VALUES (?, ?) ON CONFLICT (flat_id) DO NOTHING";
            inCreatorStatement = connection.prepareStatement(inCreatorQuery);
            inCreatorStatement.setLong(1, ClientHandler.getUserId());
            inCreatorStatement.setLong(2, generatedId);
            inCreatorStatement.executeUpdate();
            Logger.getLogger(PSQLmanager.class.getName()).info("Creator was added");


            connection.commit();
        } catch (SQLException e) {
            Logger.getLogger(PSQLmanager.class.getName()).warning("Error adding element to DB");
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException se) {
                Logger.getLogger(PSQLmanager.class.getName()).warning("Error on rollback: " + se.getMessage());
            }
        } finally {
            try {
                if (inCoordStatement != null) inCoordStatement.close();
                if (inHouseStatement != null) inHouseStatement.close();
                if (inFlatStatement != null) inFlatStatement.close();
                if (inCreatorStatement != null) inCreatorStatement.close();
            } catch (SQLException e) {
                Logger.getLogger(PSQLmanager.class.getName()).warning("Error on close: " + e.getMessage());
            }
        }
        return generatedId;
    }

    public boolean removeFlatById(long flatId) {
        try (Connection connection = ConnectionF.getConnection()) {

            String deleteFlatQuery = "DELETE FROM flat WHERE id = ? AND id IN (SELECT flat_id FROM creator WHERE user_id = ?)";
            String deleteCoordQuery = "DELETE FROM coordinates WHERE id = ?";
            String deleteHouseQuery = "DELETE FROM house WHERE id = ?";


            PreparedStatement deleteFlatStatement = connection.prepareStatement(deleteFlatQuery);
            deleteFlatStatement.setLong(1, flatId);
            deleteFlatStatement.setLong(2, ClientHandler.getUserId());
            int rowsAffectedFlat = deleteFlatStatement.executeUpdate();

            PreparedStatement deleteCoordStatement = connection.prepareStatement(deleteCoordQuery);
            deleteCoordStatement.setLong(1, flatId);
            int rowsAffectedCoord = deleteCoordStatement.executeUpdate();

            PreparedStatement deleteHouseStatement = connection.prepareStatement(deleteHouseQuery);
            deleteHouseStatement.setLong(1, flatId);
            int rowsAffectedHouse = deleteHouseStatement.executeUpdate();

            return rowsAffectedFlat > 0 && rowsAffectedCoord > 0 && rowsAffectedHouse > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public List<Long> clearFlatForUser() {
        long userId = ClientHandler.getUserId();
        List<Long> deletedFlatId = new ArrayList<>();
        try (Connection connection = ConnectionF.getConnection()) {

            String selectFlatIdQuery = "SELECT flat_id FROM creator WHERE user_id = ?";
            PreparedStatement selectedFlatIdStatement = connection.prepareStatement(selectFlatIdQuery);
            selectedFlatIdStatement.setLong(1,userId);
            ResultSet flatIdResultSet = selectedFlatIdStatement.executeQuery();
            List<Long> coordIds = new ArrayList<>();
            List<Long> houseIds = new ArrayList<>();
            while (flatIdResultSet.next()) {
                deletedFlatId.add(flatIdResultSet.getLong("flat_id"));
                coordIds.add(flatIdResultSet.getLong("flat_id"));
                houseIds.add(flatIdResultSet.getLong("flat_id"));
            }

            String deleteFlatQuery = "DELETE FROM flat WHERE id IN (SELECT flat_id FROM creator WHERE user_id = ?)";
            PreparedStatement deleteFlatStatement = connection.prepareStatement(deleteFlatQuery);
            deleteFlatStatement.setLong(1,userId);
            deleteFlatStatement.executeUpdate();

            if (!coordIds.isEmpty()) {
                String ids = coordIds.stream().map(String::valueOf).collect(Collectors.joining(","));
                String deleteCoordQuery = "DELETE FROM coordinates WHERE id IN (" + ids + ")";
                PreparedStatement deleteCoordStatement = connection.prepareStatement(deleteCoordQuery);
                deleteCoordStatement.executeUpdate();
            }

            if (!houseIds.isEmpty()) {
                String ids = houseIds.stream().map(String::valueOf).collect(Collectors.joining(","));
                String deleteHouseQuery = "DELETE FROM house WHERE id IN (" + ids + ")";
                PreparedStatement deleteHouseStatement = connection.prepareStatement(deleteHouseQuery);
                deleteHouseStatement.executeUpdate();
            }

        } catch (SQLException e) {
            Logger.getLogger(PSQLmanager.class.getName()).warning("Something wrong" + e.getMessage());
        }
        return deletedFlatId;
    }

    public boolean isFlatOwnedByUser(long flatId) {
        try (Connection connection = ConnectionF.getConnection()) {

            String checkOwnerQuery = "SELECT COUNT(*) FROM creator WHERE flat_id = ? AND user_id = ?";
            PreparedStatement checkOwnerStatement = connection.prepareStatement(checkOwnerQuery);
            checkOwnerStatement.setLong(1, flatId);
            checkOwnerStatement.setLong(2,ClientHandler.getUserId());
            ResultSet resultSet = checkOwnerStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            Logger.getLogger(PSQLmanager.class.getName()).warning("Something wrong" + e.getMessage());
            return false;
        }
        return false;
    }

    public boolean updateFlat(Flat flat) {
        try (Connection connection = ConnectionF.getConnection()) {

            connection.setAutoCommit(false);

            String updateCoordQuery = "UPDATE coordinates SET x = ?, y = ? FROM flat WHERE flat.coordinates_id = coordinates.id AND flat.id = ?";
            PreparedStatement updateCoordStatement = connection.prepareStatement(updateCoordQuery);
            updateCoordStatement.setLong(1, flat.getCoordinates().getX());
            updateCoordStatement.setDouble(2, flat.getCoordinates().getY());
            updateCoordStatement.setLong(3, flat.getId());
            updateCoordStatement.executeUpdate();

            String updateHouseQuery = "UPDATE house SET name = ?, year = ?, numberOfFlatsOnFloor = ? FROM flat WHERE flat.house_id = house.id AND flat.id = ?";
            PreparedStatement updateHouseStatement = connection.prepareStatement(updateHouseQuery);
            updateHouseStatement.setString(1, flat.getHouse().getName());
            updateHouseStatement.setInt(2, flat.getHouse().getYear());
            updateHouseStatement.setLong(3, flat.getHouse().getNumberOfFlatsOnFloor());
            updateHouseStatement.setLong(4, flat.getId());
            updateHouseStatement.executeUpdate();

            String updateFlatQuery = "UPDATE flat SET name = ?, creation_date = ?, area = ?, numberOfRooms = ?, furnish = CAST(? AS furnish_enum), view = CAST(? AS view_enum), transport = CAST(? AS transport_enum) WHERE id = ?";
            PreparedStatement updateFlatStatement = connection.prepareStatement(updateFlatQuery);
            updateFlatStatement.setString(1, flat.getName());
            updateFlatStatement.setDate(2, new java.sql.Date(flat.getCreationDate().getTime()));
            updateFlatStatement.setLong(3, flat.getArea());
            updateFlatStatement.setInt(4, flat.getNumberOfRooms());
            updateFlatStatement.setString(5, flat.getFurnish().toString());
            updateFlatStatement.setString(6, flat.getView().toString());
            updateFlatStatement.setString(7, flat.getTransport().toString());
            updateFlatStatement.setLong(8, flat.getId());
            updateFlatStatement.executeUpdate();

            connection.commit();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(PSQLmanager.class.getName()).warning("Something wrong" + e.getMessage());
        }
        return false;
    }

    public long authUser(String name, char[] passwd) {
        try (Connection connection = ConnectionF.getConnection()) {

            String selectUserQuery = "SELECT id, passwd_hash, passwd_salt FROM \"User\" WHERE name = ?";
            PreparedStatement selectUserStatement = connection.prepareStatement(selectUserQuery);
            selectUserStatement.setString(1, name);
            ResultSet resultSet = selectUserStatement.executeQuery();

            if (resultSet.next()) {
                String passwdHash = resultSet.getString("passwd_hash");
                String passwdSalt = resultSet.getString("passwd_salt");
                String inputPasswdHash = PasswdHandler.hashPassword(passwd, passwdSalt);

                if (passwdHash.equals(inputPasswdHash)) {
                    return resultSet.getLong("id");
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(PSQLmanager.class.getName()).warning("Something went wrong ");
        }
        return -1;
    }

    public long regUser(String name, char[] passwd) {
        try (Connection connection = ConnectionF.getConnection()) {

            String selectUserQuery = "SELECT COUNT(*) FROM \"User\" WHERE name = ?";
            PreparedStatement selectUserStatement = connection.prepareStatement(selectUserQuery);
            selectUserStatement.setString(1, name);
            ResultSet resultSet = selectUserStatement.executeQuery();

            if (resultSet.next() && resultSet.getInt(1) > 0) {
                return -1;
            }

            SecureRandom random = new SecureRandom();
            byte[] saltBytes = new byte[16];
            random.nextBytes(saltBytes);
            String salt = Base64.getEncoder().encodeToString(saltBytes);

            String passwdHash = PasswdHandler.hashPassword(passwd, salt);

            String inUserQuery = "INSERT INTO \"User\" (name, passwd_hash, passwd_salt) VALUES (?, ?, ?)";
            PreparedStatement inUserStatement = connection.prepareStatement(inUserQuery, Statement.RETURN_GENERATED_KEYS);
            inUserStatement.setString(1, name);
            inUserStatement.setString(2, passwdHash);
            inUserStatement.setString(3, salt);

            int rowsAffected = inUserStatement.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet generatedKeys = inUserStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(PSQLmanager.class.getName()).warning("Something wrong");
        }
        return -1;
    }
}
