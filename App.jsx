import { useState, useEffect } from "react";
import "./App.css";

function BankingApp() {
  const [action, setAction] = useState(null);
  const [amount, setAmount] = useState("");
  const [balance, setBalance] = useState(0.0);
  const [description, setDescription] = useState("");
  const [transactions, setTransactions] = useState([]);

  const API_URL = "http://localhost:8080/api";

  //  Load balance + transactions on start
  useEffect(() => {
    fetchBalance();
    fetchTransactions();
  }, []);

  //  Fetch Balance
  const fetchBalance = async () => {
    const res = await fetch(`${API_URL}/balance`);
    const data = await res.json();
    setBalance(data);
  };

  //  Fetch Transactions
  const fetchTransactions = async () => {
    const res = await fetch(`${API_URL}/transactions`);
    const data = await res.json();
    setTransactions(data);
  };

  //  Handle Submit (Deposit / Withdraw)
  const handleSubmit = async (e) => {
    e.preventDefault();

    const amt = parseFloat(amount);
    if (isNaN(amt) || amt <= 0) return;

    const endpoint = action === "deposit" ? "deposit" : "withdraw";

    try {
      const res = await fetch(`${API_URL}/${endpoint}`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          amount: amt,
          description: description,
        }),
      });

      if (!res.ok) {
        const error = await res.text();
        alert(error);
        return;
      }

      // Refresh data from backend
      fetchBalance();
      fetchTransactions();

      setAmount("");
      setDescription("");
      setAction(null);
    } catch (err) {
      console.error(err);
      alert("Error connecting to server");
    }
  };

  // Show Memo from backend data
  const showMemoAlert = () => {
    if (transactions.length === 0) {
      alert("No transactions yet.");
      return;
    }

    const memoText = transactions
      .map(
        (t, index) =>
          `${index + 1}. ${t.type} of ${t.amount} FRW\n📅 ${
            t.dateTime?.split("T")[0]
          } ⏰ ${t.dateTime?.split("T")[1]?.substring(0, 8)}\n📝 ${
            t.description
          }`
      )
      .join("\n\n");

    alert(memoText);
  };

  return (
    <div className="container">
      <h2>Balance: FRW {balance.toFixed(2)}</h2>

      <button onClick={() => setAction("deposit")} className="deposit-btn">
        DEPOSIT
      </button>
      <button onClick={() => setAction("withdraw")} className="withdraw-btn">
        WITHDRAW
      </button>

      {action && (
        <form onSubmit={handleSubmit} className="form">
          <h3>
            {action === "deposit" ? "DEPOSIT FUNDS" : "WITHDRAW FUNDS"}
          </h3>

          <input
            type="number"
            placeholder="Amount"
            value={amount}
            onChange={(e) => setAmount(e.target.value)}
            required
          />

          <input
            type="text"
            placeholder="Description"
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            required
          />

          <button
            type="button"
            className="memo-btn"
            onClick={showMemoAlert}
          >
            <h1>MEMO</h1>
          </button>

          <button type="submit" className="submit-btn">
            SUBMIT
          </button>
        </form>
      )}
    </div>
  );
}

export default BankingApp;
