import { Box, Card, CardContent, CardMedia, Typography, Avatar } from '@mui/material';

const Article = ({ image, title, description, right }) => {
    return (
        <Card sx={{ display: 'flex' }}>
            {
                !right && (
                    <CardMedia
                        component="img"
                        sx={{ width: 200 }}
                        image={image}
                        alt="Live from space album cover"
                    />
                )
            }
            <Box sx={{ display: 'flex', flexDirection: 'column' }}>
                <CardContent sx={{ flex: '1 0 auto' }}>
                    <Typography component="div" variant="h5">
                        {title}
                    </Typography>
                    <Typography variant="body2" color="text.secondary">
                        {description}
                    </Typography>
                </CardContent>
            </Box>
            {
                right && (
                    <CardMedia
                        component="img"
                        sx={{ width: 200 }}
                        image={image}
                        alt="Live from space album cover"
                    />
                )
            }
        </Card>
    )
}

export default Article;