import React, { Component } from 'react'
import VenService from '../services/VenService'

class ViewVenComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match.params.id,
            ven: {}
        }
    }

    componentDidMount(){
        VenService.getVenById(this.state.id).then( res => {
            this.setState({ven: res.data});
        })
    }

    render() {
        return (
            <div>
                <br></br>
                <div className = "card col-md-6 offset-md-3">
                    <h3 className = "text-center"> View Ven Details</h3>
                    <div className = "card-body">
                        <div className = "row">
                            <label> createdDateTime:&emsp; </label>
                            <div> { this.state.ven.createdDateTime }</div>
                        </div>
                        <div className = "row">
                            <label> modificationDateTime:&emsp; </label>
                            <div> { this.state.ven.modificationDateTime }</div>
                        </div>
                        <div className = "row">
                            <label> venName:&emsp; </label>
                            <div> { this.state.ven.venName }</div>
                        </div>
                        <div className = "row">
                            <label> ObjectType:&emsp; </label>
                            <div> { this.state.ven.objectType }</div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

export default ViewVenComponent
