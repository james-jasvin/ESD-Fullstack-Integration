import { useState, useEffect } from 'react';

import loginService from './services/login'
import billService from './services/bills'

const App = () => {
  // user state will store the logged in user object, if no login has been done yet then it will be null
  const [ user, setUser ] = useState(null)

  // Will store the bills of the logged in user
  const [ bills, setBills ] = useState([])

  // These states are used to control the notifications that show up at the top of the screen for events like 
  // login, signup, watchlist creation, etc.
  const [ notification, setNotification ] = useState(null)
  const [ notificationType, setNotificationType ] = useState(null)

  // Create a notification at the top of the screen with given message for 10 seconds 
  // Notifications are of two types, "error" and "success"
  // The appearance of these two notifications can be adjusted via the index.css file
  const notificationHandler = (message, type) => {
    setNotification(message)
    setNotificationType(type)

    setTimeout(() => {
      setNotificationType(null)
      setNotification(null)
    }, 10000)
  }

  // Function that handles login of users
  const handleLogin = async (credentials) => {
    try {
      const userObject = await loginService.login(credentials)
      setUser(userObject)

      notificationHandler(`Logged in successfully as ${userObject.name}`, 'success')
        setBills([])
    }
    catch (exception) {
      notificationHandler(exception.response.data.error, 'error')
    }
  }

  // Function that pays a bill using the billObject that is passed to the function
  const payBill = async (billObject) => {
    try {
      // Call payBill() at the backend 
      await billService.payBill(billObject)

      // On successful completion of the above method, iterate through all the bills and only retain those bills
      // which don't have ID equal to the billObject's ID, i.e. the ID of the bill that's just been paid/deleted
      setBills(bills.filter(bill => bill.id !== billObject.id))

      notificationHandler(`Successfully paid the "${billObject.name}" bill`, 'success')
    }
    catch (exception) {
      notificationHandler(exception.response.data.error, 'error')
    }
  }

  // Effect Hook that fetches a user's bills
  // If "user" state changes, then the new bills must be fetched.
  // This is why "user" is part of the dependency array of this hook
  // MIGHT HAVE TO CHANGE THIS LATER TO PROMISE CHAINING IF IT FAILS
  useEffect(() => {
      async function fetchData() {
        if (user) {
          const bills = await billService.getUserBills(user)
          setBills(bills)
        }
      }
      fetchData()
  }, [user])

  return (
    <div>
      {/* Header of the page */}
      <div className='text-center page-header p-2 regular-text-shadow regular-shadow'>
          <div className='display-4 font-weight-bold'>
            F<small>IN</small>T<small>RACK</small> {' '}
            <i className='fa fa-cog fa-spin' aria-hidden='true'></i> {' '}
          </div>
          <h5>Share investment watchlists easily!</h5>
      </div>
      
      {/* Notification component that will render only when the notification state is not null */}
      <Notification notification={notification} type={notificationType} />

      {
        /* Show Login for when no login has happened and showLoginForm is true */
        user === null && 
        <LoginForm startLogin={handleLogin}/>
      }

      {
        /* Show NavBar when login has happened */
        user !== null && 
        <NavBar user={user} setUser={setUser}/>
      } 

      {
        /* Show Bills of the User when login has happened */
        user !== null &&
        <Bills
          bills={bills}
          payBill={payBill}
        />
      }
    </div>
  )
}

export default App;
