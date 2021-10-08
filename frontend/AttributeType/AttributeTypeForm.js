import React from 'react';
import AttributeType from './AttributeType';

class AttributeTypeForm extends React.Component{

    constructor(props) {
        super(props);

        this.state = {
            //attributes
            name: '',
            isRangeAttribute: false,
            domainValues: [""],
            //control values
            disable: false,
            returnValue: ""
        }
    }

    handleChange = (event) => {
        this.setState({
            [event.target.name]: event.target.value
        });
    };

    toggleChange = () => {
        this.setState(prevState => ({
            isRangeAttribute: !prevState.isRangeAttribute
        }));
    }

    addValue = () => {
        this.setState({domainValues: [...this.state.domainValues, ""]});
    }

    handleChange2 = (e, index) =>{
        this.state.domainValues[index] = e.target.value
        this.setState({domainValues: this.state.domainValues})
    }

    onSubmit = (e) => {
        e.preventDefault();
        const data = {
            name: this.state.name,
            isRangeAttribute: this.state.isRangeAttribute,
            domainValues: this.state.domainValues
        }
        const options = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        }
        
        fetch('/attributeTypes', options)
            .then(data => data.json())
            .then(types => this.setState({returnValue: types}))

        this.toggleLook()
    }

    toggleLook = () => {
        this.setState(prevState => ({
            disable: !prevState.disable
        }));
    }

    reset = (e) => {
        this.setState({
            disable: false,
            name: '',
            isRangeAttribute: false,
            domainValues: [""]
        })
    }

    render(){
        if(!this.state.disable){
        return(
            
            <div className= 'AttributeTypeForm'>

                <h3>Create new attribute type</h3>
                <table class="black">
                    <tr>
                        <td>
                            Attribute name
                        </td>
                        <td>
                            <input name='name' onChange={this.handleChange} value={this.state.name}/>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            Ranged attribute 
                        </td>
                        <td>
                            <input type="checkbox" checked={this.state.isRangeAttribute} onChange={this.toggleChange} value={this.state.isRangeAttribute}/>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            Domain values
                        </td>
                        <td>
                            {
                                this.state.domainValues.map((value, index)=>{
                                    return(
                                        <div key={index}>
                                            <input onChange={(e)=>this.handleChange2(e, index)}
                                                value={value} />
                                        </div>
                                    )
                                })
                            }
                            <button onClick={(e) => this.addValue(e)}>Add new domain value</button>
                        </td>
                    </tr>
                </table>
                <table class="submit">
                    <tr>
                        <td>
                            <button onClick={(e) => this.onSubmit(e)}>Submit</button>
                        </td>
                    </tr>
                </table>
            </div>  
        );
        }else{
            const {message, error} = this.state.returnValue;

            if(message!=null && error!= null){
                return(
                    <div>
                        <h3>Error - {error}</h3>
                        <p>What happened: {message}</p>
                        <button onClick={(e) => this.reset(e)}>Reset</button>
                        &nbsp;
                        <button onClick={(e) => this.toggleLook(e)}>Fix</button>
                    </div>                
                    );
            }
            else{
                return(
                    <div>
                        <h3>Success</h3>
                        <AttributeType attributeType={this.state.returnValue}/>
                        <br/>
                        <button onClick={(e) => this.reset(e)}>Create another attribute type</button>
                        
                    </div>
                );
            }
        }
    } 
}

export default AttributeTypeForm;