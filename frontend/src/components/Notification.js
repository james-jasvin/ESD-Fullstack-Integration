import React from 'react'

/*
  This component renders the notification text in a <div> tag along with the relevant styles as indicated
  by the notification type
*/
const Notification = ({ notification, type }) => {
  // If the notification state is null, then don't render anything
  if (notification === null)
    return null

  // Render a <div> with the notification class style attached along with the type of notification ("success", "error")
  // The content of the div will be the text that's stored in the notification state
  return (
    <div className={`${type} notification`}>
      {notification}
    </div>
  )
}

export default Notification