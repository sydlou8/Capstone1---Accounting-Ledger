# Capstone 1 - Accounting Ledger 

## Main Class
### Screens
> **Home Screen:**
> - Add deposit
> - Add debit
> - Move to Ledger Screen
> - Exit
>
> **Ledger Screen:**
> - Display All Entries
> - Display only deposits
> - Display payments
> - Show Reports Screen
> - Show Home Screen
> 
> **Reports Screen:**
> - Show Month to Date
> - Show Previous MTD
> - Show Year to Date
> - Show Previous YTD
> - Search by Vendor
> - Move to Custom Search Screen
> - Back to Ledger Screen
> 
> **Custom Search Screen:**
> - Start Date
> - End Date
> - Description
> - Vendor
> - Amount
>
(insert image of UML Diagram)

---

### IO Usage
There are a lot of I/O usage -- 
- used a lot of userInput using scanner
- usage of PrintWriter and FileWriter to create a new csv file
- usage of Scanner to read csv lines and place it into the Transactions ArrayList
### Class Usage
I wanted to keep things organized. Even though we are storing all of our information into a log file, 
I wanted to make it easier for myself later on... I wouldn't need to constantly open and close a file
in order to retrieve information that I need if everything is stored in a Transaction class.
### List Usage
Expanding on the Transaction class, each transaction is stored in an ArrayList object. This will allow
for easier filtering and searching! For example:
```java
    // NOT MY ACTUAL CODE BUT SIMILAR TO WHAT I DID
    ArrayList <Transaction> transactions = new ArrayList<>();
    //... code that shows adds csv information into ArrayList ...
    transactions.stream()
        .filter(transaction -> transaction.getAmount > 0)
        .forEatch(transaction -> System.out.println(transaction.getDescription()));
```
This basically filters the Transactions to only show those that are Payments (negative) 
### Exception Handling
I used the try/catch statement to handle any File Exceptions and if something wasn't a correct data type.
### LocalDateTime
This was used to log the current time and date of the transaction. Will be used in later Reports.
## Transaction Class
### Private Variables
```java
    private String currentDate;
    private String currentTime;
    private String description;
    private String vendor;
    private double amount;
```
### Constructor
```java
    public Transaction(String currentDate, String currentTime, String description, String vendor, double amount) {
        this.currentDate = currentDate;
        this.currentTime = currentTime;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }
```
### Getters/Setters
The setters are kind of useless at the moment. I might add some kind of usage for the setters later on in the project.
The getters are being used for comparison in the main class.
### Methods
My only method in the Transaction class is the display method. This is so i can easily diplay the transaction object 
especially when filtering.
```java
    public void display(){
        String output = String.format("%s\t%s\t%s\t%s\t$%4.2f",
                currentDate,currentTime, description, vendor, amount);
        System.out.println(output);
    }
```
## Report Screen Logic
### MTD
This is the total money from the first day of the current _MONTH_ to the current day.
For this, I will be using the LocalDateTime to filter the transaction description and then get the 
amounts to add together for an MTD Gross Total.
### Last Month
I am assuming this is last month's total.
### YTD
This is the total money from the first day of the current _YEAR_ to the current day.
### Last Year
I am assuming this is last year's total.