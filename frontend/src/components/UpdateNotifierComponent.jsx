import React, { Component } from 'react'
import NotifierService from '../services/NotifierService';

class UpdateNotifierComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match.params.id,
        }
        this.updateNotifier = this.updateNotifier.bind(this);

    }

    componentDidMount(){
        NotifierService.getNotifierById(this.state.id).then( (res) =>{
            let notifier = res.data;
            this.setState({
            });
        });
    }

    updateNotifier = (e) => {
        e.preventDefault();
        let notifier = {
            notifierId: this.state.id,
        };
        console.log('notifier => ' + JSON.stringify(notifier));
        console.log('id => ' + JSON.stringify(this.state.id));
        NotifierService.updateNotifier(notifier).then( res => {
            this.props.history.push('/notifiers');
        });
    }


    cancel(){
        this.props.history.push('/notifiers');
    }

    render() {
        return (
            <div>
                <br></br>
                   <div className = "container">
                        <div className = "row">
                            <div className = "card col-md-6 offset-md-3 offset-md-3">
                                <h3 className="text-center">Update Notifier</h3>
                                <div className = "card-body">
                                    <form>
                                        <div className = "form-group">
                                        </div>
                                        <button className="btn btn-success" onClick={this.updateNotifier}>Save</button>
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

export default UpdateNotifierComponent
