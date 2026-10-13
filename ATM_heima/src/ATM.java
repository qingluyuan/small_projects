import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class ATM {
    private ArrayList<Account> accounts = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);
    public void start(){
        while(true){
            System.out.println("===欢迎使用ATM机===");
            System.out.println("1.用户登录");
            System.out.println("2.用户开户");
            System.out.println("请选择:");

            int choice = scanner.nextInt();
            switch(choice){
                case 1:
                    // 用户登录
                    break;
                case 2:
                    // 用户开户
                    createAccount();
                    break;
                default:
                    System.out.println("输入错误,请重新输入");
            }
        }
    }

    private void createAccount(){
        Account account = new Account();

        System.out.println("请输入您的账户名:");
        String name = scanner.next();
        account.setName(name);

        System.out.println("请输入您的性别:");
        char sex = scanner.next().charAt(0);
        while (true) {
            if(sex == '男' || sex == '女'){
                account.setSex(sex);
                break;
            }else{
                System.out.println("输入的性别有误,请重新输入:");
            }
        }

        while (true) {
            System.out.println("请输入您的密码:");
            String password = scanner.next();
            System.out.println("请您确认密码:");
            String okPassword = scanner.next();

            if(password.equals(okPassword)){
                account.setPassword(password);
                break;
            }
            else{
                System.out.println("两次输入不一致,请重新输入");
            }
        }

        System.out.println("请输入每次取款最大金额:");
        double limit = scanner.nextDouble();
        account.setLimit(limit);

        String cardId = createCardId();
        account.setCardId(cardId);

        accounts.add(account);
        System.out.println("开户成功!您的卡号为:" + account.getCardId());

    }

    private String createCardId(){
        while (true) {
            String cardId = "";
            Random random = new Random();

            for(int i = 0;i < 8;i++){
                int data = random.nextInt(10);
                cardId += data;
            }

            Account account = getAccountByCardId(cardId);
            if(account == null){
                return cardId;
            }
        }
    }

    private Account getAccountByCardId(String cardId){
        for(int i = 0;i < accounts.size();i++){
            Account account = accounts.get(i);
            if(account.getCardId().equals(cardId)){
                return account;
            }
        }
        return null;
    }
}
