import React, { Component } from 'react';

class FilterTypeProxy extends React.Component{

    render(){

        let queryType = this.props.queryType;

        let value = ""

        switch(queryType){
            case 0:
                value = "none"
                break
            case 1:
                value = "Equals"
                break
            case 2:
                value = "Not Equals"
                break
            case 3:
                value = "Greater than"
                break
            case 4:
                value = "Greater than or equals"
                break
            case 5:
                value = "Lesser than"
                break
            case 6:
                value = "Lesser than or equals"
                break
        }
        return(
            <div>
                {value}
            </div>
        );
    }

}

export default FilterTypeProxy;