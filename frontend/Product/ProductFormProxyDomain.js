import React, { Component } from 'react';

class ProductFormProxyDomain extends Component{

    constructor(props){
        super(props);

        this.state ={
            value: {}
        }

        fetch('/attributeTypes/' + this.props.id)
            .then(data => data.json())
            .then(type => this.setState({value: type}));
    }

    render(){
        if(this.state.value.rangeAttribute){
            return(
                <div>
                    ({this.state.value.domainValues[0]} - {this.state.value.domainValues[1]})
                </div>
            );
        }

         
        let size = 0;
        for(let a in this.state.value.domainValues){
            size++;
        }
        let out = "{";
        for (let i=0;i<size;i++) {
            out+= String(this.state.value.domainValues[i]);
            if(i != size-1){
                out+=", "
            }
        }
        out+="}";
        
        return(
            <div>
                {out}
            </div> 
        );  
    }
}

export default ProductFormProxyDomain;