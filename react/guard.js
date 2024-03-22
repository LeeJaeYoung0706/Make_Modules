import React, { ReactNode, useEffect } from "react";
import { deleteUserToken } from "../../store/slices/userSlice";
import { useNavigate } from "react-router-dom";
import { useAppSelector, useAppDispatch } from "../../store/hooks";
import { RootState } from "../../store";
 
interface Props {
    children: ReactNode;
}

type initialState = {
    isToken: false;
    isJoin: false;
    isLogin: false;
    token: "";
};

const UserGuard = ({ children }: Props) => {
    const dispatch = useAppDispatch();
    const navigate = useNavigate();
    const { isJoin, isToken, isLogin, token }: initialState = useAppSelector((state: RootState) => state.userSlice);

    useEffect(() => {
        if (!isLogin) {
            // 로그인 상태가 아닐 때 로그인 화면으로 이동
            if ((!isToken || token === "") && !isJoin) {
                dispatch(deleteUserToken());
                window.location.href = `${process.env.REACT_APP_REST_URL}/auth/login`;
            }
            // 홈페이지 DB 엔 없고 KeyCloak 로그인 상태일 때 회원 가입
            else if (!isJoin && isToken) {
                navigate(`/new/user`, { replace: true });
            // 이외의 상황일 때
            } else {
                dispatch(deleteUserToken());
                navigate(`/`, { replace: true });
            }
        }
    }, []);

    return <>{children}</>;
};

export default UserGuard;
