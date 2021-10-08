import React, { Component } from 'react';

import ProductFormProxy from "./ProductFormProxy";
import ProductFormProxyDomain from "./ProductFormProxyDomain";
import Product from "./Product";
import ProductsForType from "./ProductsForType";

class Products extends React.Component{

    state = {
        types: [],
        pageState: 0,

        dropDownSelectedID: 0,

        selectedProductTypeID: 0,
        selectedProductTypeName: "",
        selectedProductTypeAttIDS: [],

        newProductName: "",
        newProductAttributes: [],

        returnValue: {},

        test: ""
    };

    componentDidMount(){
        fetch('/productTypes')
            .then(data => data.json())
            .then(types => this.setState({types: types}))
    }

    selectProductType = () => {

        if(this.state.dropDownSelectedID == 0 || this.state.newProductName==""){
            return
        }
        
        let array = []

        for(let type in this.state.types){
            if(this.state.types[type].id == this.state.dropDownSelectedID){
                this.setState({selectedProductTypeID: this.state.types[type].id, 
                    selectedProductTypeName: this.state.types[type].name,
                    selectedProductTypeAttIDS: this.state.types[type].attributeIDs
                  });
                array = [...this.state.types[type].attributeIDs]
            }
        }

        this.setState({
            test: JSON.stringify(array),
            pageState: 2
          });

        let i =0;
        for(let val in array){
            this.state.newProductAttributes[i] = {"id": 0, 
                                    "value": ""}
            i++;
        }
    
    }


    backToTypes = () => {
        this.setState({
            newProductName: "",
            newProductAttributes: [],
            selectedProductTypeID: 0, 
            selectedProductTypeName: "",
            selectedProductTypeAttIDS: [],
            pageState: 0
          });
    }

    returnToMain = () => {
        this.setState({selectedProductTypeID: 0, 
                       selectedProductTypeName: "",
                       selectedProductTypeAttIDS: [],
                       pageState: 0
                     });        
    }

    handleChange = (event, id, index) => {
        if(id<0){
            this.setState({
                [event.target.name]: event.target.value
            });
        }else{
            
            this.state.newProductAttributes[index] = { "id": id,
                                                       "value": event.target.value}
            this.setState({
                newProductAttributes: this.state.newProductAttributes
            });
        }
    }

    onSubmit = (e) => {
        e.preventDefault();
        const data = {
            productTypeID: this.state.selectedProductTypeID,
            name: this.state.newProductName,
            description: this.state.newProductDescription,
            attributeValues: this.state.newProductAttributes,
            price: 0
        }
        const options = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        }
        
        fetch('/products', options)
            .then(data => data.json())
            .then(types => this.setState({returnValue: types}))

        this.displayReturnValue();
    }

    displayReturnValue = () => {
        this.setState({
            pageState: 3
        });
    }

    remake = () => {
        this.setState({
            pageState: 2,
            returnValue: {}
        });
    }

    returnToProductList = () => {
        this.setState({
            pageState: 1,
            newProductName: "",
            newProductDescription: "",
            newProductAttributes: []
        })
    }

    dropDownSelectID = (event) => {
        this.setState({
            dropDownSelectedID: event.target.value      
        })
    }

    render(){

        //displays all product types
        if(this.state.pageState == 0){
        return(
            <div>
                <h3>Create new product</h3>

                <table>
                    <tr>
                        <td>
                            Name
                        </td>
                        <td>
                            <input name='newProductName' onChange={(event) => this.handleChange(event, -2)} value={this.state.newProductName}/>
                        </td>
                    </tr>               
                    <tr>
                        <td>
                            Type
                        </td>
                        <td>
                            <select name='IDselector' onChange={(event) => this.dropDownSelectID(event)}>
                                <option value="0">none</option>
                                {
                                    this.state.types.map(param => 
                                        <option value={param.id}>{param.name}</option>
                                    )
                                }
                            </select>
                        </td>
                    </tr>
                </table>
                <table class="submit">
                    <tr>
                        <td>
                            <button onClick={() => this.selectProductType()}>Select</button>
                        </td>
                    </tr>
                </table>
            </div>
        );
        }

        //display product creation
        else if(this.state.pageState == 2){
            return(
                <div>
                    <h3>Create new product</h3>
                    <table class="black">
                        <tr>
                            <td>
                                Name
                            </td>
                            <td>
                                {this.state.newProductName}
                            </td>
                        </tr>
                        <tr>
                            <td>
                                Type
                            </td>
                            <td>
                                {this.state.selectedProductTypeName}
                            </td>
                            
                        </tr>
                    </table>

                    <table class="submit">
                        <tr>
                            <th>
                                ATTRIBUTE_NAME
                            </th>
                            <th>
                                ATTRIBUTE_DOMAIN
                            </th>
                            <th>
                                ATTRIBUTE_VALUE
                            </th>
                        </tr>
                        {
                            this.state.selectedProductTypeAttIDS.map((attID, index) =>                      
                                <tr>
                                    <td>
                                        <ProductFormProxy id={attID}/>
                                    </td>
                                    <td>
                                        <ProductFormProxyDomain id={attID}/>
                                    </td>
                                    <td>
                                        <input name = {String(index)} onChange={(event) => this.handleChange(event, attID, index)} value={this.state.newProductAttributes[index].value}/>
                                    </td>
                                </tr>                
                            )
                        }
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
        }
        //display return value when creating product
        else if(this.state.pageState == 3){
            const {message, error} = this.state.returnValue;

            if(message!=null && error!= null){
                return(
                    <div>
                        <p>{error}</p>
                        <p>{message}</p>
                        <button onClick={(e) => this.remake(e)}>Fix</button>
                        <button onClick={(e) => this.backToTypes(e)}>Reset</button>
                    </div>                
                );
            }

            return(
                <div>
                    <Product product={this.state.returnValue}/>
                    <br/>
                    <table class="blue">
                            <tr>
                                <td>
                                    <button onClick={(e) => this.backToTypes(e)}>List of products</button>
                                </td>
                            </tr>
                        </table> 
                </div>
            );
        }
    }
}

export default Products;