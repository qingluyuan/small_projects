import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class ATM {
    private ArrayList<Account> accounts = new ArrayList<>();

    Account acc = new Account("12312312","admin","123",'男',100,20);
    {
        accounts.add(acc);
    }
    Account loginAccount;
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
                    login();
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

    private void login(){
        System.out.println("===登录ATM===");
        if(accounts.isEmpty()){
            System.out.println("当前无账户,请先创建账户");
            return;
        }
        while (true) {
            System.out.println("请输入卡号:");
            String cardId = scanner.next();
            System.out.println("请输入密码:");
            String password = scanner.next();

            Account account = getAccountByCardId(cardId);
            if(account == null){
                System.out.println("卡号或密码错误,请重新输入");
            }
            else if(!password.equals(account.getPassword())){
                System.out.println("卡号或密码错误,请重新输入");
            }
            else{
                System.out.println("登录成功,您好"+account.getName());
                loginAccount = account;
                showUserCommand();
                break;
            }
        }
    }

    private void showUserCommand(){
        while (true) {
            System.out.println("===您可以选择以下功能===");
            System.out.println("1.查询账户");
            System.out.println("2.存款");
            System.out.println("3.取款");
            System.out.println("4.转账");
            System.out.println("5.密码修改");
            System.out.println("6.退出");
            System.out.println("7.注销账号");

            int choice = scanner.nextInt();

            switch(choice){
                case 1:
                    //查询账户
                    showLoginAccount();
                    break;
                case 2:
                    //存款
                    depositMoney();
                    break;
                case 3:
                    //取款
                    drawMoney();
                    break;
                case 4:
                    //转账
                    transferMoney();
                    break;
                case 5:
                    //修改密码
                    updatePassword();
                    break;
                case 6:
                    //退出
                    System.out.println(loginAccount.getName()+ ",退出成功!");
                    return;
                case 7:
                    //注销
                    if(deleteAccount()){
                        return;
                    }
                    break;
                default:
                    System.out.println("输入错误,请重新输入");
            }
        }
    }

    private void showLoginAccount(){
        System.out.println("卡号:" + loginAccount.getCardId());
        System.out.println("户主:" + loginAccount.getName());
        System.out.println("性别:" + loginAccount.getSex());
        System.out.println("余额:" + loginAccount.getMoney());
        System.out.println("每次提现额度:" + loginAccount.getLimit());
    }

    private void depositMoney(){
        System.out.println("===存钱===");
        System.out.println("请输入存款金额:");
        double money = scanner.nextDouble();

        loginAccount.setMoney(loginAccount.getMoney() + money);
    }

    private void drawMoney(){
        while (true) {
            System.out.println("===取钱===");
            System.out.println("请输入取款金额:");
            double money = scanner.nextDouble();

            if(money > loginAccount.getMoney()){
                System.out.println("您的余额不足");
            }else if(money > loginAccount.getLimit()){
                System.out.println("取款金额超过限额");
            }else{
                loginAccount.setMoney(loginAccount.getMoney() - money);
                return;
            }
        }
    }

    private void transferMoney(){
        while (true) {
            System.out.println("===转账功能===");
            if(loginAccount.getMoney() == 0){
                System.out.println("您的账号没有余额");
                return;
            }

            System.out.println("请输入要转入的卡号:");
            String cardId = scanner.next();

            Account account = getAccountByCardId(cardId);

            if(account == null){
                System.out.println("您输入的对方卡号不存在");
            }
            else{
                System.out.println("请输入您要转入的金额:");
                double money = scanner.nextDouble();

                if(money > loginAccount.getMoney()){
                    System.out.println("您的余额不足");
                }
                else{
                    loginAccount.setMoney(loginAccount.getMoney() - money);
                    account.setMoney(account.getMoney() + money);
                    System.out.println("转账成功");
                    return;
                }
            }


        }
    }

    private boolean deleteAccount(){
        System.out.println("===销户操作===");
        System.out.println("确定要销户吗?此操作无法撤回(y/n)");
        String command = scanner.next();

        if (command.equals("y")) {
            if(loginAccount.getMoney() != 0) {
                System.out.println("您的账户中余额不为0,无法注销");
                return false;
            }
            else{
                accounts.remove(loginAccount);
                System.out.println("销户成功");
                return true;
            }
        }
        System.out.println("保留账户!");
        return false;
    }

    private void updatePassword(){
        while (true) {
            System.out.println("===密码修改===");
            System.out.println("请输入当前密码:");
            String password = scanner.next();

            if(password.equals(loginAccount.getPassword())){
                while (true) {
                    System.out.println("请输入新密码:");
                    String newPassword = scanner.next();
                    System.out.println("请确认新密码:");
                    String newOkPassword = scanner.next();

                    if(newPassword.equals(newOkPassword)){
                        loginAccount.setPassword(newPassword);
                        System.out.println("修改成功");
                        return;
                    }
                    else{
                        System.out.println("两次密码不同,请重新输入");
                    }
                }
            }
            else{
                System.out.println("您输入的密码不正确");
            }
        }
    }
}
