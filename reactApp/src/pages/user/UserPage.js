import * as React from 'react';
import {
    Box,
    Button,
    Dialog,
    DialogActions,
    DialogContent,
    DialogContentText,
    DialogTitle,
    Stack,
    Tab,
    Tabs
} from "@mui/material";
import {FormatListBulleted, Logout, Person} from "@mui/icons-material";
import {TabReposElement} from "./elements/TabReposElement";
import {TabProfileElement} from "./elements/TabProfileElement";
import {ConstantImages, ConstantKMM, LanguageContext, useLocalStorage} from "../../base";
import {ValueType} from "../../base/route/ValueType";
import {useContext} from "react";

interface TabPanelProps {
    children?: React.ReactNode;
    index: number;
    value: number;
}

function TabPanel(props: TabPanelProps) {
    const {children, value, index, ...other} = props;

    return (
        <div
            style={{
                width: '100%',
                height: '100%'
            }}
            role="tabpanel"
            hidden={value !== index}
            id={`vertical-tabpanel-${index}`}
            aria-labelledby={`vertical-tab-${index}`}
            {...other}
        >
            {value === index && (
                <Box sx={{
                    width: '100%',
                    height: '100%'
                }}>
                    {children}
                </Box>
            )}
        </div>
    );
}

function a11yProps(index: number) {
    return {
        id: `vertical-tab-${index}`,
        'aria-controls': `vertical-tabpanel-${index}`,
    };
}

export function UserPage() {

    const {t} = useContext(LanguageContext)
    const darkMode = useLocalStorage("darkMode", ValueType.bool);

    const [value, setValue] = React.useState(0);
    const [openLogout, setOpenLogout] = React.useState(false);

    const [modelsRepos, setModelsRepos] = React.useState([]);
    const [modelsFollowers, setModelsFollowers] = React.useState([]);
    const [modelUser, setModelUser] = React.useState(null);

    const handleClickOpen = () => {
        setOpenLogout(true)
    };

    const handleClose = () => {
        setOpenLogout(false)
    };

    return (
        <React.Fragment>
            <Box
                sx={{flexGrow: 1, bgcolor: 'background.paper', display: 'flex', height: '100%'}}
            >
                <Stack sx={{
                    backgroundColor: 'secondary.light',
                    position: 'relative'
                }}>
                    <Box align={"center"} sx={{
                        paddingTop: '13px',
                        paddingBottom: '7px',
                        backgroundColor: 'secondary.main',
                    }}>
                        <img style={{
                            maxWidth: 40
                        }} src={ConstantImages.layout.logo} alt={'Logo'}/>
                    </Box>

                    <Tabs
                        orientation="vertical"
                        value={value}
                        onChange={(event: React.SyntheticEvent, newValue: number) => {
                            setValue(newValue);
                        }}
                        aria-label="Tabs menu"
                        sx={{
                            width: 110,
                            borderRight: 1,
                            borderColor: 'divider',
                            '& .Mui-selected': {
                                color: darkMode ? 'white !important' : 'black !important',
                            },
                        }}
                    >
                        <Tab icon={<FormatListBulleted sx={{
                            backgroundColor: 'secondary.main',
                            padding: '4px 18px',
                            borderRadius: 5,
                            marginTop: 1,
                        }}/>} label={t('repos.title')} {...a11yProps(0)} />
                        <Tab icon={<Person sx={{
                            backgroundColor: 'secondary.main',
                            padding: '4px 18px',
                            borderRadius: 5,
                            marginTop: 1,
                        }}/>} label={t('profile.title_profile')} {...a11yProps(1)} />
                    </Tabs>

                    <Button variant="contained" color="secondary" style={{boxShadow: "none"}} sx={{
                        borderRadius: 0,
                        paddingTop: '22px',
                        paddingBottom: '22px',
                        position: 'absolute',
                        bottom: 0,
                        left: 0,
                        right: 0
                    }} onClick={handleClickOpen}>
                        <Logout/>
                    </Button>
                </Stack>

                <TabPanel value={value} index={0}>
                    <TabReposElement
                        models={modelsRepos}
                        updateModels={(models) => {
                            setModelsRepos(models)
                        }}
                    />
                </TabPanel>
                <TabPanel value={value} index={1}>
                    <TabProfileElement
                        user={modelUser}
                        updateUser={(model) => {
                            setModelUser(model)
                        }}
                        models={modelsFollowers}
                        updateModels={(models) => {
                            setModelsFollowers(models)
                        }}
                    />
                </TabPanel>
            </Box>

            <Dialog
                open={openLogout}
                onClose={handleClose}
                aria-labelledby="alert-dialog-title"
                aria-describedby="alert-dialog-description"
            >
                <DialogTitle id="alert-dialog-title">
                    {t('common.dialog_logout_title')}
                </DialogTitle>
                <DialogContent>
                    <DialogContentText id="alert-dialog-description">
                        {t('common.dialog_logout_desc')}
                    </DialogContentText>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose}>
                        {t('common.dialog_btn_dismiss')}
                    </Button>
                    <Button onClick={() => {
                        handleClose()
                        ConstantKMM.crossStorage.authToken = ""
                        window.location = "/"
                    }} autoFocus>
                        {t('common.dialog_btn_confirm')}
                    </Button>
                </DialogActions>
            </Dialog>
        </React.Fragment>
    );
}

UserPage.propTypes = {};