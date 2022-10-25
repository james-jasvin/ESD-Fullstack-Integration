import axios from 'axios'

// The API endpoint where bills are located
const billsUrl = `http://localhost:3001/api/bills`

// Gets all watchlists which belong to a user
const getUserWatchlistData = async (user) => {
  // Get bills of the given user, using query parameter, ?user={user.id}
  const response = await axios.get(`${billsUrl}?user=${user.id}`)
  
  // The .data field would now contain the bills of the users
  return response.data
}

// Pays the bill which is specified, after paying, the record of the bill is deleted
// So this translates to a delete request from axios to the bill API endpoint at the backend
const payBill = async (bill) => {
  const response = await axios.delete(`${billsUrl}/${bill.id}`)
  return response.data
}

const exportObject = { getUserWatchlistData, payBill }

export default exportObject