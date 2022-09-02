import { Outlet } from "react-router-dom"
import Navbar from "./components/navbar/Navbar"

function Layout() {
    return (
        <main>
            <Navbar />
            <Outlet />
        </main>
    )
}

export default Layout