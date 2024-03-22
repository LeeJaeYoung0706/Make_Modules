import React, { useCallback, useState } from "react";

type searchFormHookResultType = [
    useSearchFormvariableInterface,
    (type: string) => (value: eventValueType) => void,
    () => void,
    (type: string) => (value: eventValueType) => void,
    (fun: (value: any) => void) => (value: string) => void
];

const useSearchForm: (initVariable: useSearchFormvariableInterface) => searchFormHookResultType = initVariable => {
    const [schVariable, setSchVariable] = useState<useSearchFormvariableInterface>(initVariable);

    let timer: NodeJS.Timeout | null;

    const throttle: (type: string) => (value: eventValueType) => void = useCallback(
        type => value => {
            if (timer) return;
            timer = setTimeout(() => {
                setSchVariable(before => {
                    return {
                        ...before,
                        [type]: typeof value === "string" && !Number.isNaN(value) ? value : Number(value)
                    };
                });
                clearTimeout(timer as NodeJS.Timeout);
                timer = null;
            }, 500);
        },
        [schVariable]
    );

    const debounce: (type: string) => (value: eventValueType) => void = useCallback(
        type => value => {
            if (timer) clearTimeout(timer as NodeJS.Timeout);
            timer = setTimeout(() => {
                setSchVariable(before => {
                    return {
                        ...before,
                        [type]: typeof value === "string" && !Number.isNaN(value) ? value : Number(value)
                    };
                });
                timer = null;
            }, 500);
        },
        [schVariable]
    );

    const variableChange: (type: string) => (value: eventValueType) => void = useCallback(
        type => value => {
            setSchVariable(before => {
                return {
                    ...before,
                    [type]: typeof value === "string" && !Number.isNaN(value) ? value : Number(value)
                };
            });
        },
        []
    );

    const reset = useCallback(() => setSchVariable(initVariable), [initVariable]);

    const enterKeyDown = useCallback(
        (fun: (value: any) => void) => (value: string) => {
            fun(value);
        },
        []
    );

    return [schVariable, variableChange, reset, debounce, enterKeyDown];
};

export default useSearchForm;
