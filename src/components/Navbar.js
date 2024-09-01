import { AppBar, Box, Button, Toolbar } from "@mui/material";
import { useEffect, useState } from "react";
import { useHistory } from "react-router-dom/cjs/react-router-dom.min";

const Navbar = () => {
    const history = useHistory();

    const currentPath = history.location.pathname;
    const [openLinkName, setOpenLinkName] = useState('');

    useEffect(() => {
        const path = history.location.pathname;
        setOpenLinkName(path);
    }, [history.location.pathname]);

    const links = [
        { name: 'Home', path: '/' },
        { name: 'About', path: '/about' },
        { name: 'Documentation', path: '/docs' },
        // { name: 'Contact', path: '/contact' }
    ];

    return (
        <AppBar position="static" zIndex={1000} color="transparent">
            <Toolbar sx={{ display: 'flex', justifyContent: 'center' }}>
                <Box sx={{ display: { xs: 'none', md: 'flex' }, gap: '5vw' }}>
                    {links.map(link => (
                        <Button
                            key={link.name}
                            sx={{
                                color: openLinkName === link.path ? '#fff' : '#aaa',
                                '&:hover': {
                                    color: openLinkName === link.path ? '#fff' : '#ccc',
                                    backgroundColor: 'transparent',
                                    cursor: openLinkName === link.path ? 'default' : 'pointer'
                                }
                            }}
                            onClick={() => {
                                setOpenLinkName(link.path);
                                history.push(link.path);
                            }}
                            disableRipple
                        >
                            {link.name}
                        </Button>
                    ))}
                </Box>
            </Toolbar>
        </AppBar>
    );
}

export default Navbar;