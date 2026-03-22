/*设计一个 Java 自定义异常类：

用于处理银行账户余额不足的情况。
当用户尝试提取超过其账户余额的金额时，抛出该异常，并在异常中提供错误信息。
要求：

        （1）自定义异常类名为 InsufficientBalanceException。
        （2）该异常类应继承自 Exception 类。
        （3）异常类中需要包含一个带有错误信息参数的构造函数。
        （4）在抛出异常时，错误信息应包含账户号码和尝试提取的金额。
 */

class InsufficientBalanceException extends Exception {
    public InsufficientBalanceException(String message){
        super (message);
    }
}

class BankAccount {
    private String accountNumber;
    private double balance;
    public BankAccount(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public void withdraw(double amount) throws InsufficientBalanceException {
        if (amount > balance) {
            String errorMessage = "账户 " + accountNumber + " 余额不足，当前余额: " + balance +
                    "，尝试提取金额: " + amount;
            throw new InsufficientBalanceException(errorMessage);
        }
        balance -= amount;
        System.out.println("提取成功！提取金额: " + amount + "，当前余额: " + balance);
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
}

    public class work0001_q02 {
        public static void main(String[] args) {
            BankAccount account = new BankAccount("1234567890", 1000.0);

            try {
                System.out.println("当前账户: " + account.getAccountNumber() +
                        "，当前余额: " + account.getBalance());

                account.withdraw(500.0);
                account.withdraw(600.0);
            } catch (InsufficientBalanceException e) {
                System.out.println("错误: " + e.getMessage());
            }
        }
}
