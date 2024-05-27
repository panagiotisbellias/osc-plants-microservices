import { ToastContainer } from "react-toastify";
import { AppContainer } from "./router/App.styles";
import { UserContextProvider } from "./context/UserContext";
import AppRouter from "./router/App.router";
import { logger } from './logger';

window.onbeforeunload = function () {
  localStorage.clear();
  logger.info('Local storage has been cleared');
};

function App() {
    logger.info('App component has been rendered');

    return (
    <AppContainer>
      <UserContextProvider>
        <AppRouter />
        <ToastContainer />
      </UserContextProvider>
    </AppContainer>
    );
}

export default App;
