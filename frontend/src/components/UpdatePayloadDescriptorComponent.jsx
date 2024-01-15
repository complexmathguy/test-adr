import React, { Component } from 'react'
import PayloadDescriptorService from '../services/PayloadDescriptorService';

class UpdatePayloadDescriptorComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match.params.id,
        }
        this.updatePayloadDescriptor = this.updatePayloadDescriptor.bind(this);

    }

    componentDidMount(){
        PayloadDescriptorService.getPayloadDescriptorById(this.state.id).then( (res) =>{
            let payloadDescriptor = res.data;
            this.setState({
            });
        });
    }

    updatePayloadDescriptor = (e) => {
        e.preventDefault();
        let payloadDescriptor = {
            payloadDescriptorId: this.state.id,
        };
        console.log('payloadDescriptor => ' + JSON.stringify(payloadDescriptor));
        console.log('id => ' + JSON.stringify(this.state.id));
        PayloadDescriptorService.updatePayloadDescriptor(payloadDescriptor).then( res => {
            this.props.history.push('/payloadDescriptors');
        });
    }


    cancel(){
        this.props.history.push('/payloadDescriptors');
    }

    render() {
        return (
            <div>
                <br></br>
                   <div className = "container">
                        <div className = "row">
                            <div className = "card col-md-6 offset-md-3 offset-md-3">
                                <h3 className="text-center">Update PayloadDescriptor</h3>
                                <div className = "card-body">
                                    <form>
                                        <div className = "form-group">
                                        </div>
                                        <button className="btn btn-success" onClick={this.updatePayloadDescriptor}>Save</button>
                                        <button className="btn btn-danger" onClick={this.cancel.bind(this)} style={{marginLeft: "10px"}}>Cancel</button>
                                    </form>
                                </div>
                            </div>
                        </div>

                   </div>
            </div>
        )
    }
}

export default UpdatePayloadDescriptorComponent
