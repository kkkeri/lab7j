//package manager.Command;
//
//import manager.Receiver;
//import system.Request;
//
//;
///**
// * Данная команда позваляет сохранить изменения внесенную в коллекцию
// * @see BaseCommand
// * @author keri
// * @since 1.0
// */
//public class SaveCommand implements BaseCommand{
//    @Override
//    public String execute(Request request) throws Exception {
//        try {
//            return Receiver.saveData(request);
//        } catch (Exception e) {
//            return e.getMessage();
//        }
//    }
//
//    @Override
//    public String getName() {
//        return "save ";
//    }
//
//    @Override
//    public String getDescription() {
//        return "Save collection to json file";
//    }
//}
