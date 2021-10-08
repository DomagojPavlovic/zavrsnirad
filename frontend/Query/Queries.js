import React, { Component } from 'react';

import ProductsForType from "../Product/ProductsForType";

import ProductFormProxy from "../Product/ProductFormProxy";
import ProductFormProxyDomain from "../Product/ProductFormProxyDomain";
import GenerateDropdowns from './GenerateDropdowns';

import ProductsFromQueryDisplay from './ProductsFromQueryDisplay';
import QueryHistory from './QueryHistory';

class Queries extends React.Component{

    state = {
        types: [],
        pageState: 0,
        selectedProductTypeID: 0,
        selectedProductTypeName: "",
        selectedProductTypeAttIDS: [],

        queryValues: [],

        returnValue: {},
        
        test: "",
        test2: ""
    };

    componentDidMount(){
        fetch('/productTypes')
            .then(data => data.json())
            .then(types => this.setState({types: types}))
    }

    selectProductType = (param) => {

        this.setState({selectedProductTypeID: param.id, 
                       selectedProductTypeName: param.name,
                       selectedProductTypeAttIDS: param.attributeIDs,
                       pageState: 1
                     });
    }

    backToTypes = () => {
        this.setState({
            pageState: 0
          });
    }

    executeQueryPressed = () => {

        let index = 0;
        for(let attID in this.state.selectedProductTypeAttIDS){
            this.state.queryValues[index] = {
                attributeId: this.state.selectedProductTypeAttIDS[index],
                filterType: 0,
                filterValue: "",
                sortPriority: 1
            }
            index++;
        }

        this.setState({
            pageState: 2
        });
    }

    getHistoryPressed = () => {

        let index = 0;
        for(let attID in this.state.selectedProductTypeAttIDS){
            this.state.queryValues[index] = {
                attributeId: this.state.selectedProductTypeAttIDS[index],
                filterType: 0,
                filterValue: "",
                sortPriority: 1
            }
            index++;
        }

        this.setState({
            pageState: 4
        })
    }

    filterTypeChange = (id, event, index) => {
        let array = this.state.queryValues;
        array[index].filterType = event.target.value  
        this.setState({
            queryValues: array
        })
    }

    filterValueChange = (id, event, index) => {
        let array = this.state.queryValues;
        array[index].filterValue = event.target.value  
        this.setState({
            queryValues: array
        })
    }

    sortPrioChange = (id, event, index) => {
        let array = this.state.queryValues;
        array[index].sortPriority = event.target.value
        this.setState({
            queryValues: array
        })
    }

    onSubmit = (e) => {
        //e.preventDefault();

        let outputArray = []
        for(let element in this.state.queryValues){
            let elem = this.state.queryValues[element];
            outputArray.push({
                attributeTypeID: elem.attributeId,
                priority: parseFloat(elem.sortPriority),
                queryType: parseFloat(elem.filterType),
                queryValue: elem.filterValue,
            })
        }


        const data = {
            userID: 1,
            productTypeID: this.state.selectedProductTypeID,
            queryElements: outputArray
        }

        this.setState({test: JSON.stringify(data)})

        
        const options = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        }
        
        fetch('/searchQueries', options)
            .then(data => data.json())
            .then(types => this.setState({returnValue: types}))

        this.displayReturnValue(); 
    }

    reExecute = (query) => {

        this.setState({
            queryValues: []
        });

        let array = []
        let index = 0
        for(let element in query.queryElements){
            let single = query.queryElements[element]
            this.state.queryValues[index] = {
                attributeId: single.attributeTypeID,
                filterType: single.queryType,
                filterValue: single.queryValue,
                sortPriority: single.priority
            }
            index++
        }

        this.setState({
            selectedProductTypeID: query.productTypeID,
        });

        this.onSubmit();
    }

    displayReturnValue = () => {
        this.setState({
            pageState: 3,
            queryValues: []
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
            returnValue: {}
        })
    }

    render(){

        //displays all product types
        if(this.state.pageState == 0){
            return(
                <div>
                    <h3>ProductTypes</h3>
                    <table class ="black">
                    {
                        this.state.types.map(param => 
                            <tr>
                                <td>
                                    {param.name}
                                </td>
                                <td>
                                    <button onClick={() => this.selectProductType(param)}>Select</button>
                                </td>
                            </tr>                                             
                        )
                    }
                    </table>
                </div>
            );
        }

        //displays select product type and its children
        else if(this.state.pageState == 1){
            return(
                <div>
                    <h3>Product: {this.state.selectedProductTypeName}</h3>
                    
                    <button onClick={() => this.executeQueryPressed()}>New query</button>
                    &nbsp;
                    <button onClick={() => this.getHistoryPressed()}>Query history</button>
                    &nbsp;
                    <button onClick={() => this.backToTypes()}>Select another type</button>                            
                    <br/>
                    <br/>
                    <ProductsForType id={this.state.selectedProductTypeID}/>
                </div>
            );
        }

        else if(this.state.pageState == 2){

            return(
                <div>
                    <h3>New query {this.state.testID} {this.state.testValue}</h3>

                    <table class= "black">
                        <tr>
                            <th>
                                Attribute name
                            </th>
                            <th>
                                Attribute domain
                            </th>
                            <th>
                                Filter type
                            </th>
                            <th>
                                Filter value
                            </th>
                            <th>
                                Filter priority
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
                                        <GenerateDropdowns id={attID} fnc={this.filterTypeChange} index={index}/>
                                    </td>
                                    <td>
                                        <input name={attID} onChange={(event) => this.filterValueChange(attID, event, index)} value={this.state.queryValues[index].queryValue}/>
                                    </td>
                                    <td>
                                        <select name={attID} onChange={(event) => this.sortPrioChange(attID, event, index)}>
                                            <option value="0">0</option>
                                            <option value="1">1</option>
                                            <option value="2">2</option>
                                            <option value="3">3</option>
                                        </select>
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

        else if(this.state.pageState == 3){
            const {message, error} = this.state.returnValue;

            if(message!=null && error!= null){
                return(
                    <div>
                        <p>{error}</p>
                        <p>{message}</p>
                        <button onClick={(e) => this.remake(e)}>Fix</button>
                        <button onClick={(e) => this.returnToProductList(e)}>List of products</button>
                    </div>                
                );
            }

            let value = {
                values: this.state.returnValue
            }

            return(
                <div>
                    <ProductsFromQueryDisplay product={value}/>
                    <br/>
                    <table class="blue">
                            <tr>
                                <td>
                                    <button onClick={(e) => this.returnToProductList(e)}>List of products</button>
                                </td>
                            </tr>
                        </table> 
                </div>
            );
        }

        //display query history
        else if( this.state.pageState == 4){

            return(
                <div>
                    <h3>Query history</h3>
                    <QueryHistory reExecute={this.reExecute}/>
                </div>
            );
        }
    }
}

export default Queries;