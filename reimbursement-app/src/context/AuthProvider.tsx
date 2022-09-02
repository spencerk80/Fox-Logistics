import React, { createContext, useState } from "react"

export interface AuthProviderProps {
    children: React.ReactNode
}

export interface AuthProviderContext {
    auth: {},
    setAuth: (prevState: {}) => void
}

const defaultContext: AuthProviderContext = {
    auth: {},
    setAuth: () => {}
}

export const AuthContext = createContext<AuthProviderContext>(defaultContext)

const {Provider} = AuthContext

const AuthProvider: React.FC<AuthProviderProps> = ({children}) => {
    const [auth, setAuth] = useState({})

    return (
        <Provider value={{auth, setAuth}}>
            {children}
        </Provider>
    )
}

export default AuthProvider