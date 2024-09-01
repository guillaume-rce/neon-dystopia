import { Card, CardMedia, Stack, Avatar, AvatarGroup } from "@mui/material";
import { Octokit } from "octokit";
import { useState, useEffect } from "react";

import "./Banner.css";

const Banner = () => {
    const [watchers, setWatchers] = useState([]);

    const config = null;

    const repositoryOwner = config?.repositoryOwner;
    const repositoryName = config?.repositoryName;

    useEffect(() => {
        const fetchWatchers = async () => {
            try {
                const octokit = new Octokit({
                    auth: config?.githubToken
                })

                const response = await octokit.request('GET /repos/{owner}/{repo}/subscribers', {
                    owner: repositoryOwner,
                    repo: repositoryName,
                    headers: {
                        'X-GitHub-Api-Version': '2022-11-28'
                    }
                })
                const watchersData = response.data.map(watcher => ({
                    avatarUrl: watcher.avatar_url,
                    username: watcher.login // `login` est la propriété qui contient le nom d'utilisateur GitHub
                }));
                setWatchers(watchersData);
            } catch (error) {
                console.error('Error fetching watchers', error);
            }
        };

        fetchWatchers();
    }, []);

    return (
        <Stack direction="column" className="banner" spacing={2}>
            <Card className="banner-card">
                <CardMedia
                    component="img"
                    height="250"
                    image="https://images.unsplash.com/photo-1722648404131-a69c35a706fa?q=80&w=2940&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90oy1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
                    alt="random"
                />
            </Card>
            {
                watchers.length > 0 && (
                    <Stack direction="row" spacing={2} className="banner-info">
                        <AvatarGroup>
                            {watchers.slice(0, 5).map((watcher, index) => (
                                <Avatar key={index} alt={watcher.username} src={watcher.avatarUrl} />
                            ))}
                            {watchers.length > 10 && (
                                <Avatar>+{
                                    watchers.length > 1000 ?
                                        Math.floor(watchers.length / 1000) + "k" :
                                        watchers.length
                                }</Avatar>
                            )}
                        </AvatarGroup>
                    </Stack>
                )
            }
        </Stack>
    );
}

export default Banner;
