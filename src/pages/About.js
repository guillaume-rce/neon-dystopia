import { Stack } from "@mui/material"

import "./share.css"
import Navbar from "../components/Navbar";
import Banner from "../components/about/Banner";

const About = () => {
    return (
        <Stack spacing={2} orientation="vertical" className="background about">
            <Navbar />
            <Banner />
        </Stack>
    );
}

export default About;