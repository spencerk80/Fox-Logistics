import React, { useContext, useState } from "react"
import axios from '../../api/axios'
import {AuthContext} from '../../context/AuthProvider'

export interface TicketProps {
    id: string,
    employeeId: string,
    amount: number,
    category: string,
    userComment: string,
    status: string,
    timeStamp: string
}

const Ticket: React.FC<TicketProps> = ({id, employeeId, amount, category, userComment, status, timeStamp}) => {

    const {auth} = useContext(AuthContext)
    const [curStatus, setCurStatus] = useState(status)

    async function handleClick(event: React.MouseEvent<HTMLButtonElement>) {
        const newStatus: string = event.currentTarget.id

        try {
            const res = await axios.put(
                '/tickets', 
                {
                    id: id,
                    employeeId: employeeId,
                    amount: amount,
                    category: category,
                    userComment: userComment,
                    status: newStatus,
                    timeStamp: timeStamp
                },
                {
                    headers: {
                        'Content-Type': 'application-json',
                        'Authorization': 'bearer ' + auth.jwt
                    }
                }
            )

            if(res.status != 200) throw new Error("Failed to update")

            setCurStatus(newStatus)
        } catch(err) {

        }
    }
    
    return (
        <table>
            <tr>
                <td>
                    Ticket: {id}
                </td>
            </tr>
            <tr>
                <td>
                    Employee: {employeeId}
                </td>
            </tr>
            <tr>
                <td>
                    Amount: {amount}
                </td>
                <td>
                    Category: {category}
                </td>
            </tr>
            <tr>
                <td>
                    status: {curStatus}
                </td>
                <td>
                    timestamp: {timeStamp}
                </td>
            </tr>
            <tr>
                <td>
                    Comment: {userComment}
                </td>
            </tr>
            {status === 'PENDING' && <tr><td><button id="ACCEPTED" onClick={handleClick}>Approve</button><button id="DENIED" onClick={handleClick}>Deny</button></td></tr>}
        </table>
    )
}

export default Ticket