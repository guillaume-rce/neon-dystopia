import { Card, CardContent, CardMedia, Typography } from "@mui/material"

const InfoCard = ({ image, title, description }) => {
    return (
        <Card
            sx={{ backgroundColor: "var(--ColorTheme_NeonDystopia-5)" }}
        >
            <CardMedia
                component="img"
                height="200"
                image={image}
                alt="random"
            />
            <CardContent>
                <Typography gutterBottom variant="h5" component="div" color="white">
                    {title}
                </Typography>
                <Typography variant="body2" sx={{ textAlign: 'justify' }} color="white">
                    {description}
                </Typography>
            </CardContent>
        </Card>
    )
}

export default InfoCard;