const apiValidation = async (url ,
                             httpRequestKind,
                             params,
                             paramsValidationFunction,
) => {
    if (paramsValidationFunction != null) {
        const paramValidationResult = await paramsValidationFunction(params);
        if (!paramValidationResult.isResult)
            return {
                isResult: false,
                data: paramValidationResult.message
            }
    }

    switch (httpRequestKind) {
        case "post": {
            return await Api.post(url , params).then( async result => {
                const resultCheck = await resultValidation(result).catch(reason => {
                    return {
                        isResult: false,
                        data: reason.message
                    }
                });

                if (resultCheck)
                    return {
                        isResult: true,
                        data: result.response
                    }
            })
        }
    }


}

export default apiValidation;
