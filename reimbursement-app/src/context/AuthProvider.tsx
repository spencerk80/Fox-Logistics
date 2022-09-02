import React, { ReactElement } from "react"
import { createContext, useState } from "react"

type AuthProviderProps = {
    children: React.ReactNode
}

const AuthContext = createContext({})

export const AuthProvider: React.FunctionComponent<AuthProviderProps> = ({children}) => {
    const [auth, setAuth] = useState({})

    return (
        <AuthContext.Provider value={{auth, setAuth}}>
            {children}
        </AuthContext.Provider>
    )
}

export default AuthContext