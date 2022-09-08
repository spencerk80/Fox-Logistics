import axios from "axios"
import { TicketProps } from "../components/ticket/Ticket"

export interface TicketDataType {
    totalItems: number,
    tickets: Array<TicketProps>,
    totalPages: number,
    currentPage: number
}

export default axios.create({
    baseURL: 'http://localhost:8080/api'
})