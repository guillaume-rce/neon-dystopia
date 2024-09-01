import { Stack } from "@mui/material";

import "./share.css"
import Navbar from "../components/Navbar";
import Welcome from "../components/home/Welcome";
import Informations from "../components/home/Informations";

const Home = () => {
    return (
        <Stack spacing={2} orientation="vertical" className="background home">
            <Navbar />
            <Welcome />
            <Informations />
        </Stack>
    );
}

export default Home;