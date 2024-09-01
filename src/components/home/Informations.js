import { Stack } from "@mui/material"
import InfoCard from "./InfoCard"

import "./Informations.css"

const Informations = () => {
    return (
        <Stack direction={{ xs: 'column', md: 'row' }}
            spacing={4} className="cards-container" >
            <InfoCard
                image="https://cdn-icons-png.flaticon.com/512/5930/5930147.png"
                title="About the game"
                description="Neon Dystopia is a puzzle and maze game where you have to find the exit of a maze by activating some mechanisms.
                    You'll advance from floor to floor, avoiding traps and pitfalls, all in retro-wave style."
            />
            <InfoCard
                image="https://cdn-icons-png.freepik.com/512/3090/3090423.png"
                title="About the community"
                description="Neon Dystopia is a community game, where everyone can contribute to the game.
                    You can simply create levels, or even contribute to the game's code and content. The game needs you!"
            />
        </Stack>
    )
}

export default Informations;