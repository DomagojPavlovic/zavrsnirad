import React, { Component } from 'react';
import "../App/App.css"

class AttributeType extends Component{

    render(){
        const {id, name, rangeAttribute, domainValues} = this.props.attributeType;

        var size = 0;
        var arr = [];
        for (var key in domainValues) {
            arr.push(domainValues[key]);
            size++;
        }
        if(rangeAttribute){
            return(
                <div>
                    {name}: {domainValues[0]}-{domainValues[1]}
                </div>
            );
        }

        var out = ""
        if(size == 1){
            out+= String(domainValues[0]);
        }else{

        out += "{";
        for (var i=0;i<size;i++) {
            out+= String(domainValues[i]);
            if(i != size-1){
                out+=", "
            }
        }
        out += "}";
        }
        return(
            <div>
                {name}: {out}
            </div>
        );
    }

}

export default AttributeType;