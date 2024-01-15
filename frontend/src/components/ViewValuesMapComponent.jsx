import React, { Component } from 'react'
import ValuesMapService from '../services/ValuesMapService'

class ViewValuesMapComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match.params.id,
            valuesMap: {}
        }
    }

    componentDidMount(){
        ValuesMapService.getValuesMapById(this.state.id).then( res => {
            this.setState({valuesMap: res.data});
        })
    }

    render() {
        return (
            <div>
                <br></br>
                <div className = "card col-md-6 offset-md-3">
                    <h3 className = "text-center"> View ValuesMap Details</h3>
                    <div className = "card-body">
                        <div className = "row">
                            <label> type:&emsp; </label>
                            <div> { this.state.valuesMap.type }</div>
                        </div>
                        <div className = "row">
                            <label> values:&emsp; </label>
                            <div> { this.state.valuesMap.values }</div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

export default ViewValuesMapComponent
