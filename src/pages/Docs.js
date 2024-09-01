import React from 'react';
import { Button, Card, Stack, Typography } from '@mui/material';

import Navbar from '../components/Navbar';
import Grid2 from '@mui/material/Unstable_Grid2/Grid2';

const Docs = () => {
    return (
        <Stack spacing={2} orientation="vertical" className="background home">
            <Navbar />
            <Grid2 container spacing={2}>
                <Grid2 item xs={12} sm={6}>
                    <Card className="docs-card">
                        <Typography variant="h4">JavaDoc of the game</Typography>
                        <Typography variant="body1">
                            This is the JavaDoc of the game. It contains all the classes, methods, and attributes of the game.
                            It explains how it works and how you can use it. It is useful if you want to contribute to the game and develop new features.
                        </Typography>
                        <Button
                            variant="outlined"
                            size="large"
                            className="welcome-button contribute"
                            href="https://guillaume-rce.github.io/Neon-Dystopia-JavaDoc/"
                            target="_blank"
                        >
                            Access the JavaDoc
                        </Button>
                    </Card>
                </Grid2>
                <Grid2 item xs={12} sm={6}>
                    <Card className="docs-card">
                        <Typography variant="h4">GitHub repository</Typography>
                        <Typography variant="body1">
                            This is the GitHub repository of the game. It contains all the source code of the game.
                            It is useful if you want to contribute to the game and develop new features.
                        </Typography>
                        <Button
                            variant="outlined"
                            size="large"
                            className="welcome-button contribute"
                            href="https://github.com/guillaume-rce/Neon-Dystopia/"
                            target="_blank"
                        >
                            Access the repository
                        </Button>
                    </Card>
                </Grid2>
                <Grid2 item xs={12} sm={6}>
                    <Card className="docs-card">
                        <Typography variant="h4">ReadMe of the game</Typography>
                        <Typography variant="body1">
                            This is the ReadMe of the game. It contains all the information about the game.
                            It explains how it works and how you can use it. It is useful if you want to contribute to the game and develop new features.
                        </Typography>
                        <Button
                            variant="outlined"
                            size="large"
                            className="welcome-button contribute"
                            href="https://github.com/guillaume-rce/Neon-Dystopia/blob/main/README.md"
                            target="_blank"
                        >
                            Access the ReadMe
                        </Button>
                    </Card>
                </Grid2>
            </Grid2>
        </Stack>
    );
}

export default Docs;