import { Button, Stack, SvgIcon, Typography } from "@mui/material";

import Logo from "../../resources/NeonDystopia1.png"
import GitHubIcon from '@mui/icons-material/GitHub';
import { ReactComponent as SteamIcon } from '../../resources/icons/steam.svg';
import { ReactComponent as DiscordIcon } from '../../resources/icons/discord.svg';

import "./Welcome.css"

const Welcome = () => {
    return (
        <Stack orientation="vertical" className="welcome-backgroud">
            <img src={Logo} alt="Neon Dystopia" className="welcome-logo" />
            <Typography variant="body1" className="welcome-text">
                Want to play a puzzle and maze game in a retro-futuristic universe?
            </Typography>
            <Typography variant="body1" className="welcome-text title">
                Welcome to Neon Dystopia!
            </Typography>
            <Typography variant="body1" className="welcome-text">
                An open-source community game where you can contribute to its development.
            </Typography>
            <Stack direction="row" spacing={2} className="welcome-buttons">
                <Button
                    variant="outlined"
                    size="large"
                    className="welcome-button play"
                    startIcon={<SvgIcon component={SteamIcon} inheritViewBox />}
                >
                    Play
                </Button>
                <Button
                    variant="outlined"
                    size="large"
                    className="welcome-button contribute"
                    startIcon={<GitHubIcon />}
                    href="https://github.com/guillaume-rce/Neon-Dystopia"
                    target="_blank"
                >
                    Contribute
                </Button>
                <Button
                    variant="outlined"
                    size="large"
                    className="welcome-button join"
                    startIcon={<SvgIcon component={DiscordIcon} inheritViewBox />}
                >
                    Join
                </Button>
            </Stack>
        </Stack>
    );
}

export default Welcome;