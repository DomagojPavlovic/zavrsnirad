import React from 'react';
import AttributeType from "../AttributeType/AttributeType"
import ProductType from "./ProductType"

class ProductTypeForm extends React.Component{

    constructor(props){
        super(props);

        this.state = {
            //attributes
            name: '',
            attributeIDs: [],
            //control values
            disable: false,
            returnValue: "",
            selectedAttributeTypes: [],
            availableAttributeTypes: [],
            allAttributeTypes: []
        }
    }

    componentDidMount(){
        fetch('/attributeTypes')
            .then(data => data.json())
            .then(types => this.setState({allAttributeTypes: types})); 
        fetch('/attributeTypes')
            .then(data => data.json())
            .then(types => this.setState({availableAttributeTypes: types})); 
    }

    displayButtonLabel = function(type){
        let name = type.name
        let rangeAttribute = type.rangeAttribute
        let domainValues = type.domainValues

        if(rangeAttribute){
            return name + " " + domainValues[0] + "-" + domainValues[1];
        }

        var size = domainValues.length
        var out = "{";
        for (var i=0;i<size;i++) {
            out+= String(domainValues[i]);
            if(i != size-1){
                out+=", "
            }
        }
        out+="}";

        return (name + " " + out);
    }

    swapToSelected = (id) =>{
        let array = [...this.state.availableAttributeTypes];
        let toSwap = array[id];
        array.splice(id, 1);
        let array2 = [...this.state.selectedAttributeTypes, toSwap];
        this.setState({availableAttributeTypes: array});
        this.setState({selectedAttributeTypes: array2});
    }

    swapToAvailable = (id) => {
        let array = [...this.state.selectedAttributeTypes];
        let toSwap = array[id];
        array.splice(id, 1);
        let array2 = [...this.state.availableAttributeTypes, toSwap];
        this.setState({selectedAttributeTypes: array});
        this.setState({availableAttributeTypes: array2});
    }

    handleChangeName = (event) => {
        this.setState({
            [event.target.name]: event.target.value
        });
    };

    onSubmit = (e) => {
        e.preventDefault();

        let array = [];
        
        var size = this.state.selectedAttributeTypes.length
        for(let i = 0; i< size; i++){
            array.push(this.state.selectedAttributeTypes[i].id);
        }

        const data = {
            name: this.state.name,
            attributeIDs: array
        }

        const options = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        }
        
        fetch('/productTypes', options)
            .then(data => data.json())
            .then(types => this.setState({returnValue: types}))

        this.toggleLook()
    }

    toggleLook = () => {
        this.setState(prevState => ({
            disable: !prevState.disable
        }));
        if(this.state.disable){
            this.reset();
        }
    }

    reset = () => {
        this.setState({
            name: '',
            selectedAttributeTypes: [],
            availableAttributeTypes: [...this.state.allAttributeTypes]
        })
    }

    render(){
        if(!this.state.disable){
        return(
            <div className= 'ProductTypeForm'>
                <h3>Create new product type</h3>
                <div>
                <table class="black">
                    <tr>
                        <td>
                            Name
                        </td>
                        <td>
                            <input name='name' onChange={this.handleChangeName} value={this.state.name}/>
                        </td>
                    </tr>
                </table>
                <table class="submit">
                    <tr>
                        <td>
                            <b>Selected attributes</b>
                        </td>
                    </tr>
                </table>
                <table class="submit">
                    <tr>
                        <td>
                            {
                                this.state.selectedAttributeTypes.map((type, i) => 
                                <button onClick={() => this.swapToAvailable(i)}>       
                                    {
                                        this.displayButtonLabel(type)
                                    }
                                </button>)
                            }
                        </td>
                    </tr>
                </table>
                <table class="submit">
                    <tr>
                        <td>
                            <b>Available attributes</b>
                        </td>
                    </tr>
                </table>
                <table class="submit">
                    <tr>
                        <td>
                            {
                                this.state.availableAttributeTypes.map((type, i) => 
                                <button onClick={() => this.swapToSelected(i)}>                         
                                    {
                                        this.displayButtonLabel(type)
                                    }
                                </button>)
                            }
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
            </div>
        );
        }else{

            let array = {att: [...this.state.selectedAttributeTypes]};

            const {message, error} = this.state.returnValue;

            if(message!=null && error!= null){
                return(
                    <div>
                        <h3>Error - {error}</h3>
                        <p>What happened: {message}</p>
                        <button onClick={(e) => this.toggleLook(e)}>Create new product type</button>
                    </div>                
                    );
            }
            else{
                return(
                    <div>
                        <h3>Success</h3>
                        <ProductType 
                            productType={this.state.returnValue} 
                            attrs={ array }/>
                        <br/>                        
                        <button onClick={(e) => this.toggleLook(e)}>Create another product type</button>
                    </div>
                );
            }
        }
    }
}

export default ProductTypeForm;