import java.util.*;

class Transaction {
    String hash;
    String from;
    String to;
    double amount;

    Transaction(String hash, String from, String to, double amount) {
        this.hash = hash;
        this.from = from;
        this.to = to;
        this.amount = amount;
    }
}

class WalletTracker {
    Map<String, List<Transaction>> transactionGraph = new HashMap<>();

    void addTransaction(Transaction tx) {
        transactionGraph.putIfAbsent(tx.from, new ArrayList<>());
        transactionGraph.get(tx.from).add(tx);
    }

    Set<String> tracePath(String startWallet, int depth) {
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        queue.add(startWallet);
        int level = 0;

        while (!queue.isEmpty() && level < depth) {
            int size = queue.size();
            for (int I = 0; I < size; I++) {
                String currentWallet = queue.poll();
                if (!visited.contains(currentWallet)) {
                    visited.add(currentWallet);
                    List<Transaction> txs = transactionGraph.getOrDefault(currentWallet, new ArrayList<>());
                    for (Transaction tx : txs) {
                        queue.add(tx.to);
                    }
                }
            }
            level++;
        }

        return visited;
    }
}

public class Main {
    public static void main(String[] args) {
        WalletTracker tracker = new WalletTracker();

        tracker.addTransaction(new Transaction("tx1", "walletA", "wallet", 1.0));
        tracker.addTransaction(new Transaction("tx2", "wallet", "walletC", 1.0));
        tracker.addTransaction(new Transaction("tx3", "walletC", "walletD", 1.0));
        tracker.addTransaction(new Transaction("tx4", "wallet", "wallet", 0.5));

        Set<String> result = tracker.tracePath("walletA", 3);

        for (String wallet : result) {
            System.out.println(wallet);
        }
    }
}
    
    