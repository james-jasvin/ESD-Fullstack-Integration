import React from 'react'
import Bill from './Bill'

/*
  This component is used for rendering the "Bills" view which contains each Bill of the Student
  Each Bill is its own component
  
  bills: Collection of bills of the given Student
  payBill: Method that uses the axios service to pay the specified bill, i.e. send a DELETE request
*/
const Bills = ({ bills, payBill }) => {  
  // If there's no Bills for the student, then render nothing. Can instead render a message like "No Bills Remaining"
  if (bills === [])
    return null

  return (
    <div className='m-5 p-2 rounded regular-shadow' id="bills">
      <h2 className='ml-2'>Your Bills</h2>
      { 
        // Render each Bill as its separate component and for this you use the map() method
        // Whenever you use the map() method to render a collection of Components, React requires that you specify a unique
        // attribute of each component in this collection and for this case, we are using the id of each Bill as the key
        // for React to uniquely identify each Bill
        // We also pass on the Bill object that has to be rendered by the component and the payBill method that will
        // execute the payment
        bills.map(b =>
          <Bill
            bill={b}
            key={b.id}
            payBill={payBill}
          /> 
        )
      }
    </div>
  )
}

export default Bills