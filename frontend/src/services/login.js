import axios from 'axios'

// The API endpoint where login data is sent to
const loginBaseUrl = `http://localhost:8081/api/student/login`

const login = async (credentials) => {
  // Send the login credential data to the loginBaseUrl API endpoint as an HTTP POST request
  // Note the async-await
  const response = await axios.post(loginBaseUrl, credentials)
  
  // Return .data field of the response object which would contain the logged in user object
  // And the user state would now be updated by the handleLogin() method
  return response.data
}

// Export the method as an object so that it can be accessible outside this file as a service
const exportObject = { login }
export default exportObject