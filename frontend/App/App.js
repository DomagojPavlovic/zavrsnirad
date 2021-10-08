import React, { Component } from 'react';
import './App.css'
import { BrowserRouter as Router, Route, Link } from "react-router-dom";

import AttributeTypes from "../AttributeType/AttributeTypes";
import AttributeTypeForm from "../AttributeType/AttributeTypeForm";

import ProductTypes from "../ProductType/ProductTypes";
import ProductTypeForm from "../ProductType/ProductTypeForm";

import Products from '../Product/Products';
import Queries from '../Query/Queries';

import About from '../About/About'

class App extends Component{
    
    render(){
        return(
            <div className="App">
                <h1>Evaluator</h1>
                <hr/>
                <Router>
                    <div class="split left">
                        <div class="vertical-menu">
                            <h3 class="vertical-menu">Navigation</h3>   
                            <hr/>                
                            <Link to="/newAttributeType">Create new attribute type</Link>  
                            <Link to="/attributeTypesList">List all attribute types</Link>                            
                            <hr/>
                            <Link to="/newProductType">Create new product type</Link>
                            <Link to="/productTypesList">List all product types</Link>
                            <hr/>
                            <Link to="/products">Create new product</Link>
                            <Link to="/queries">List all products</Link>
                            <hr/>
                            <Link to="/about">About</Link>                             
                        </div>
                    </div>

                    <div class="split right">
                        <Route path="/about" component={About}/>
                        <Route path="/attributeTypesList" component={AttributeTypes}/>
                        <Route path="/newAttributeType" component={AttributeTypeForm}/>
                        <Route path="/productTypesList" component={ProductTypes}/>
                        <Route path="/newProductType" component={ProductTypeForm}/>
                        <Route path="/products" component={Products}/>
                        <Route path="/queries" component={Queries}/>
                    </div>
                </Router>
            </div>
        );
    }
}

export default App;