import {useEffect, useMemo, useState} from "react";

const getSeconds = (time) => {
    const seconds = Number(time % 60);
    if(seconds < 10) {
        return "0" + String(seconds);
    } else {
        return String(seconds);
    }
}
const initTime = 30

const Timer = ({handleIsShow , isShow , handleData}) => {
    const [time, setTime] = useState(initTime); // 남은 시간 (단위: 초)

    useEffect(() => {
        if(isShow) {
            const timer = setInterval(() => {
                setTime((prev) => prev - 1);
            }, 1000);

            if (time < 0) {
                handleIsShow(() => false)
                setTime(() => initTime)
                handleData( () => {
                    authIdx: -1,
                    phone: "",
                    token: ""
                });
                setTimeout(() => {
                    alert("시간이 초과되었습니다")
                } , 200)
            }
            return () => clearInterval(timer);
        }
    }, [time , isShow]);
    return (
        <div>
            <h1>남은 시간</h1>
            <div>
                <span>{parseInt(time / 60)}</span>
                <span> : </span>
                <span>{getSeconds(time)}</span>
            </div>
        </div>
    );
}

export default Timer;
