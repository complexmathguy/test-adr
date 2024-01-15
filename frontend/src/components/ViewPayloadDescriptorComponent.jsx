import React, { Component } from 'react'
import PayloadDescriptorService from '../services/PayloadDescriptorService'

class ViewPayloadDescriptorComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match.params.id,
            payloadDescriptor: {}
        }
    }

    componentDidMount(){
        PayloadDescriptorService.getPayloadDescriptorById(this.state.id).then( res => {
            this.setState({payloadDescriptor: res.data});
        })
    }

    render() {
        return (
            <div>
                <br></br>
                <div className = "card col-md-6 offset-md-3">
                    <h3 className = "text-center"> View PayloadDescriptor Details</h3>
                    <div className = "card-body">
                    </div>
                </div>
            </div>
        )
    }
}

export default ViewPayloadDescriptorComponent
